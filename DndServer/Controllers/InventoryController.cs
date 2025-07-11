using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using DndServer.Dal;
using DndServer.User.Services;
using DndServer.Inventory.Models;
using DndServer.Inventory.Services;

namespace DndServer.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class InventoryController : ControllerBase
    {
        AuthSql authSql = new AuthSql();
        ClaimValidator claimValidator = new ClaimValidator();
        InventoryService inventoryService = new InventoryService();

        private readonly ILogger<PlayerController> _logger;

        public InventoryController(ILogger<PlayerController> logger)
        {
            {
                _logger = logger;
            }
        }

        [HttpPost("CreateInventoryItem")]
        public async Task<ActionResult<CreateInventoryItemResponse>> CreateInventoryItem(CreateInventoryItemRequest request)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(request.UserId, token, authSql);

            var response = new CreateInventoryItemResponse();

            if (claimAccepted)
            {
                var itemId = inventoryService.CreateInventoryItemAndGetId(request);

                response.ItemId = itemId;
                response.Message = "item added successfully";
                response.Success = true;

                //TODO handle failure to add

                return Ok(response);
            }
            else
            {
                response.Message = "Invalid user";
                return BadRequest(response); //TODO return a better response than a string.
            }
        }

        //EditItem
//GetItems
//DeleteItem
//AddItemToCampaign
//AddItemToPlayer

    }
}

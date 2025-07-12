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



                //TODO handle failure to add
                if(itemId == 0)
                {
                    response.Message = "item failed to add";

                    return Ok(response);
                }
                else
                {
                    response.ItemId = itemId;
                    response.Message = "item added successfully";
                    response.Success = true;

                    return Ok(response);
                }

            }
            else
            {
                response.Message = "Invalid user";
                return BadRequest(response);
            }
        }
        [HttpPut("UpdateInventoryItem")]
        public async Task<ActionResult<InventoryBaseResponse>> UpdateInventoryItem(UpdateInventoryItemRequest request)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(request.UserId, token, authSql);

            var response = new InventoryBaseResponse();
            if (claimAccepted)
            {
                var success = inventoryService.UpdateInventoryItem(request);
                if (success)
                {
                    response.Success = true;
                    response.Message = "Update Successful";
                    return Ok(response);
                }
                else
                {
                    response.Message = "update failed";
                    return Ok(response);
                }
            }
            else
            {
                response.Message = "Invalid user";
                return BadRequest(response);
            }
        }

        [HttpGet("GetCampaignInventory/{userId}/{campaignId}")]
        public async Task<ActionResult<GetInventoryItemsForCampaignResponse>> GetInventoryItemsForCampaign([System.Web.Http.FromUri] int userId, [System.Web.Http.FromUri] int campaignId)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(userId, token, authSql);

            var response = new GetInventoryItemsForCampaignResponse();
            if (claimAccepted)
            {
                var itemList = inventoryService.GetInventoryItems(userId, campaignId);
                if(itemList != null)
                {
                    response.Success = true;
                    response.Message = "successfully retrieved items";
                    response.InventoryItems = itemList;

                    return Ok(response);
                }
                else
                {
                    response.Message = "unable to retrieve items";
                    return Ok(response);
                }
            }
            else
            {
                response.Message = "Invalid user";
                return BadRequest(response);
            }

        }

        //DeleteItem
        //AddItemToPlayer
        //GetPlayerInventory

        //AddItemToCampaign - TODO

    }
}

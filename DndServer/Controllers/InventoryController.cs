using DndServer.Campaign.Models;
using DndServer.Dal;
using DndServer.Inventory.Models;
using DndServer.Inventory.Services;
using DndServer.User.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

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

        [HttpDelete("DeleteInventoryItem/{userId}/{itemId}")]
        public async Task<ActionResult<InventoryBaseResponse>> DeleteInventoryItem([System.Web.Http.FromUri] int userId, [System.Web.Http.FromUri] int itemId)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(userId, token, authSql);

            var response = new InventoryBaseResponse();
            if (claimAccepted)
            {
                inventoryService.DeleteInventoryItem(userId, itemId, response);
                return Ok(response);
            }
            else
            {
                response.Message = "Invalid user";
                return BadRequest(response);
            }
        }

        [HttpPatch("AddItemToPlayer")]
        public async Task<ActionResult<InventoryBaseResponse>> AddItemToPlayer(AddItemToPlayerRequest request)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(request.UserId, token, authSql);

            var response = new InventoryBaseResponse();
            if (claimAccepted)
            {
                inventoryService.assignItemToPlayer(request, response);
                return Ok(response);
            }
            else
            {
                response.Message = "Invalid user";
                return BadRequest(response);
            }

        }

        [HttpGet("GetPlayerItems/{userId}/{playerId}/{campaignId}")]
        public async Task<ActionResult<GetInventoryItemsForCampaignResponse>> GetPlayerItems([System.Web.Http.FromUri] int userId,[System.Web.Http.FromUri] int playerId, [System.Web.Http.FromUri] int campaignId)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(userId, token, authSql);

            var response = new GetInventoryItemsForCampaignResponse();
            if (claimAccepted)
            {
                inventoryService.getPlayerInventoryItems(playerId, campaignId, response);
                return Ok(response);
            }
            else
            {
                response.Message = "Invalid user";
                return BadRequest(response);
            }


        }
    }
}

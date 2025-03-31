using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using DndServer.Campaign.Models;
using DndServer.Dal;
using DndServer.Campaign.Services;
using System.Net;
using System.Diagnostics;
using DndServer.Player.Models;
using DndServer.Player.Services;
using DndServer.User.Services;

namespace DndServer.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class PlayerController : ControllerBase
    {
        AuthSql authSql = new AuthSql();
        ClaimValidator claimValidator = new ClaimValidator();

        private readonly ILogger<PlayerController> _logger;

        public PlayerController(ILogger<PlayerController> logger)
        {
            {
                _logger = logger;
            }
        }

        //TODO amend the below to eventually add player to campaign, already has the logic to validate campaign etc
        /*[HttpPost("CreatePlayer")]
        public async Task<ActionResult<Response>> CreatePlayer(NewCharacterModel request)
        {

            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(request.UserId, token, authSql);

            PlayerServices services = new PlayerServices();
            Response response = new Response();

            if (services.ValidateRoomCode(request.RoomCode))
            {
                response.ResponseString = services.AddPlayer(request);
            }
            else
            {
                response.ResponseString = "Invalid room Code";
            }
            response.StatusCode = HttpStatusCode.OK;
            return response;

        }*/

        [HttpPost("CreatePlayer")]
        public async Task<ActionResult<Response>> CreatePlayer(NewCharacterModel request)
        {

            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(request.UserId, token, authSql);

            if (claimAccepted)
            {

                PlayerServices services = new PlayerServices();
                CreatePlayerResponse response = new CreatePlayerResponse();

                var playerId = services.AddPlayer(request);
                if (playerId != 0)
                {
                    response.PlayerId = playerId;
                    response.Success = true;
                    return Ok(response);
                }
                else
                {
                    return Ok(response);
                }
            }
            else
            {
                return BadRequest("Invalid user"); //TODO return a better response than a string.
            }
        }
        //TODO editPlayer, deletePlayer, addPlayerToCampaign, getPlayer
        [HttpGet("GetPlayers/{userId}")]
        public async Task<ActionResult<Response>> GetPlayers([System.Web.Http.FromUri] int userId)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(userId, token, authSql);

            if (claimAccepted)
            {
                PlayerServices services = new PlayerServices();

                var response = services.GetPlayers(userId);

                return Ok(response);
            }
            else
            {
                return BadRequest("Invalid user");
            }
        }

        [HttpDelete("DeletePlayer")]
        public async Task<ActionResult<Response>> DeletePlayer(DeletePlayerModel request)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(request.UserId, token, authSql);

            if (claimAccepted)
            {
                PlayerServices services = new PlayerServices();

                var response = new PlayerBaseResponse();
                var sqlResponse = services.DeletePlayer(request);
                if(sqlResponse)
                {
                    response.Success = true;
                    response.Message = "Character Deleted";
                }
                else
                {
                    response.Success = false;
                    response.Message = "Character not deleted";
                }
                return Ok(response);
            }
            else
            {
                return BadRequest("Invalid user");
            }

        }
    }
}

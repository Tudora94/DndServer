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

namespace DndServer.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class PlayerController : ControllerBase
    {
        private readonly ILogger<PlayerController> _logger;

        public PlayerController(ILogger<PlayerController> logger)
        {
            {
                _logger = logger;
            }
        }
        [HttpPost("CreatePlayer")]
        public async Task<ActionResult<Response>> CreatePlayer(NewCharacterModel model)
        {
            PlayerServices services = new PlayerServices();
            Response response = new Response();

            if (services.ValidateRoomCode(model.RoomCode))
            {
                response.ResponseString = services.AddPlayer(model);
            }
            else
            {
                response.ResponseString = "Invalid room Code";
            }
            response.StatusCode = HttpStatusCode.OK;
            return response;

        }
    }
}

using Microsoft.AspNetCore.Mvc;
using DndServer.Campaign;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace DndServer.Controllers
{

    [ApiController]
    [Route("[controller]")]
    public class CampaignController : ControllerBase
    {

        private readonly ILogger<CampaignController> _logger;

        public CampaignController(ILogger<CampaignController> logger)
        {
            _logger = logger;
        }

        [HttpGet(Name = "GetCampaignDetails")]
        public async Task<Campaign.Campaign> CampaignDetails()



        {
            DndServer.Campaign.Campaign camp = new DndServer.Campaign.Campaign();

            return camp;
        }
    }
}
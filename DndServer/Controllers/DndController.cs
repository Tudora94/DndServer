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

    [ApiController]
    [Route("[controller]")]
    public class AllowedRaceController : ControllerBase
    {
        private readonly ILogger<AllowedRaceController> _logger;

        public AllowedRaceController(ILogger<AllowedRaceController> logger)
        {
            _logger = logger;
        }
        [HttpGet(Name = "GetAllowedRaces")]
        public async Task<List<int>> GetAllowedRaces()
        {
            DndServer.Campaign.CampaignPreferences camp = new DndServer.Campaign.CampaignPreferences();
            List<int> Response = camp.Sources;

            return Response;
        }
        
        [HttpPost(Name = "SetAllowedRace")]
        public IActionResult Post([FromBody] Campaign.Campaign campaign)
        {
            DndServer.Campaign.Campaign camp = new DndServer.Campaign.Campaign();
            int iD = campaign.CampaignID;

            return Ok(iD);
        }
    }
}
using Microsoft.AspNetCore.Mvc;
using DndServer.Campaign;
using System.Text.Json;
using System.Text.Json.Serialization;
using DndServer.Dal;
using Microsoft.Extensions.Logging;
using Microsoft.AspNetCore.Authorization;

namespace DndServer.Controllers
{

    /*[ApiController]
    [Route("[controller]")]
    [Authorize]
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
            _logger.LogInformation("Hello World for Get Request");

            return camp;
        }
    }*/

    [ApiController]
    [Route("[controller]")]
    [Authorize]

    public class AllowedRaceController : ControllerBase
    {
        private readonly ILogger<AllowedRaceController> _logger;

        public AllowedRaceController(ILogger<AllowedRaceController> logger)
        {
            _logger = logger;
        }

        [HttpPost(Name = "SetAllowedRace")]
        public IActionResult Post([FromBody] Campaign.CampaignModel campaign)
        {
            DndServer.Campaign.CampaignModel camp = new DndServer.Campaign.CampaignModel();
            SqlDal sqlDal = new SqlDal();

            _logger.LogInformation("Hello World.");



            String LocalCampaignName = campaign.CampaignName;

            int iD = sqlDal.getCampaignId(LocalCampaignName);

            return Ok(iD);
        }
        [HttpGet(Name = "GetAllowedRaces")]
        public async Task<List<int>> GetAllowedRaces()
        {
            DndServer.Campaign.CampaignPreferencesModel camp = new DndServer.Campaign.CampaignPreferencesModel();
            List<int> Response = camp.AllowedRaces;

            return Response;
        }
    }
}
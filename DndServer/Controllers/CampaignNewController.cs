using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using DndServer.Campaign.Models;
using DndServer.Dal;


namespace DndServer.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CampaignNewController : ControllerBase
    {
        [HttpPost("CreateCampaign")]
        [Authorize]
        public async Task<ActionResult<int>> createCampaign(CreateCampaignModel request)
        {
            CampaignSql campaignSql = new CampaignSql();

            int CampaignId = campaignSql.CreateCampaign(request);

            request.CampaignId = CampaignId;

            return Ok(request);
        }
    }
}

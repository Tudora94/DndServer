using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using DndServer.Campaign.Models;
using DndServer.Dal;
using System.Net;

namespace DndServer.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CampaignNewController : ControllerBase
    {
        CampaignSql campaignSql = new CampaignSql();


        [HttpPost("CreateCampaign")]
        [Authorize]
        public async Task<ActionResult<int>> createCampaign(CreateCampaignModel request)
        {

            int CampaignId = campaignSql.CreateCampaign(request);

            request.CampaignId = CampaignId;

            return Ok(request);
        }

        [HttpGet("GetCampaigns/{userName}")]
        [Authorize]
        public async Task<ActionResult<List<CreateCampaignModel>>> getCampagins([System.Web.Http.FromUri] string userName) //pass in UserId
        {
            CampaignListModel responseList = new CampaignListModel();

            responseList = campaignSql.getCampaigns(userName);

            return Ok(responseList.CampaignModels);
        }

        [HttpGet("GetCampaign/{userName}/{campaignId}")]
        [Authorize]
        public async Task<ActionResult<CampaignModel>> getCampaign([System.Web.Http.FromUri] string userName, int campaignId)
        {
            //GetCampaignModel
        }
    }
}

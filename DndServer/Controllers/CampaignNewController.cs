using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using DndServer.Campaign.Models;
using DndServer.Dal;
using DndServer.Campaign.Services;
using System.Net;
using System.Diagnostics;

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
            //Check if CampaignName Exists

            var nameCheck = campaignSql.checkCampaignName(request.Name, request.UserName1);

            if (!nameCheck)
            {
                return BadRequest("Campaign Name already exists");
            }

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

        [HttpGet("GetCampaign/{campaignId}")]
        [Authorize]
        public async Task<ActionResult<CampaignModel>> getCampaign([System.Web.Http.FromUri] int campaignId)
        {
            //GetCampaignModelServices
            CampaignGeneratorService camp = new CampaignGeneratorService();
            var campaignResponse = camp.Campaign(campaignId);
            return Ok(campaignResponse);

        }

        [HttpPost("SetCampaignAttributes")]
        [Authorize]
        public async Task<ActionResult<Response>> setCampaignAttributes(CampaignModel model)
        {
            Response response = new Response();
            CampaignSql campaignSql = new CampaignSql();

            response.ResponseString = campaignSql.setCampaignAttributes(model);

            response.StatusCode = HttpStatusCode.OK;

            return response;
        }

        [HttpPost("GenerateCampaignCode")]
        [Authorize]
        public async Task<ActionResult<CampaignCode>> setCampaignCode(CampaignIdModel model)
        {
            int campaignId = model.CampaignId;
            CampaignCode code = new CampaignCode();
            CodeGeneratorService gen = new CodeGeneratorService();
            CampaignSql campaignSql = new CampaignSql();

            gen.generator();
            var randomCode = gen.GeneratedCode;
            code.CampaignRoomCode = randomCode;

            campaignSql.setRoomCode(code, campaignId);

            return Ok(code);
        }

        [HttpGet("GetPlayers/{campaignId}")]
        [Authorize]
        public async Task<ActionResult<List<CampaignPlayerModel>>> getPlayers([System.Web.Http.FromUri] int campaignId)
        {
            List<CampaignPlayerModel> players = new List<CampaignPlayerModel>();
            CampaignSql campaignSql = new CampaignSql();
            players = campaignSql.getPlayers(campaignId);

            return Ok(players);
        }
    }
}

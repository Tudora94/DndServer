using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using DndServer.Campaign.Models;
using DndServer.Dal;
using DndServer.Campaign.Services;
using System.Net;
using System.Diagnostics;
using DndServer.User.Services;
using System.Security.Claims;

namespace DndServer.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CampaignNewController : ControllerBase
    {
        CampaignSql campaignSql = new CampaignSql();
        AuthSql authSql = new AuthSql();
        ClaimValidator claimValidator = new ClaimValidator();


        [HttpPost("CreateCampaign")]
        [Authorize]
        public async Task<ActionResult<int>> createCampaign(CreateCampaignRequestModel request)
        {
            //Check if CampaignName Exists
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(request.UserId, token, authSql);

            var response = new CreateCampaignResponseModel();
            response.UserId = request.UserId;
            response.Name = request.Name;
            response.UpdateTime = request.UpdateTime;

            if (claimAccepted)
            {

                var username = authSql.getUserNameFromId(request.UserId);

                var nameCheck = campaignSql.checkCampaignName(request.Name, request.UserId);

                if (!nameCheck)
                {
                    return BadRequest("Campaign Name already exists");
                }

                int CampaignId = campaignSql.CreateCampaign(username, request.Name, request.UpdateTime);

                response.CampaignId = CampaignId;

                return Ok(response);
            }
            return BadRequest("invalid User");
        }

        [HttpGet("GetCampaigns/{userId}")]
        [Authorize]
        public async Task<ActionResult<List<CreateCampaignResponseModel>>> getCampagins([System.Web.Http.FromUri] int userId) //pass in UserId
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(userId, token, authSql);

            if (claimAccepted)
            {

                CampaignListModel responseList = new CampaignListModel();

                responseList = campaignSql.getCampaigns(userId);

                return Ok(responseList.CampaignModels);
            } else
            {
                return BadRequest();
                //TODO update bad request to be better.
            }
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

        [HttpPost("DeleteCampaign")]
        [Authorize]
        public async Task<ActionResult<List<BaseResponse>>> deleteCampaign(CampaignDeleteRequest request)
        {
            var token = HttpContext.Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var claimAccepted = claimValidator.validateClaimUser(request.UserId, token, authSql);

            var response = new BaseResponse();

            if (claimAccepted) {

                CampaignSql campaignSql = new CampaignSql();
                campaignSql.deleteCampaign(request.CampaignId);

                response.Success = true;
                response.Message = "Campaign Deleted";

                return Ok(response);

            }
            else {
                response.Success = false;
                response.Message = "Invalid User";
            return BadRequest(response);
            }

        }
    }
}

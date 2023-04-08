using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;


namespace DndServer.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]

    public class CampaignController : ControllerBase
    {
        //HTTP POST CreateCampaign - receive : createCampaignModel => userName string, CampaignName string - return : CampaignId
        //GTTP GET GetCampaign - receive : userName - return : List<CampaignListModel> => CampaignId int, CampaignName String
        //HTTP POST SetCampaignDetails - Campaign Model
        //HTTP POST GenerateCampaignCode -
    }
}

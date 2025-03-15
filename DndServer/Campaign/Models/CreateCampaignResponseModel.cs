namespace DndServer.Campaign.Models
{
    public class CreateCampaignResponseModel : CreateCampaignRequestModel
    {
        private int _campaignId;

        public int CampaignId { get => _campaignId; set => _campaignId = value; }
    }
}

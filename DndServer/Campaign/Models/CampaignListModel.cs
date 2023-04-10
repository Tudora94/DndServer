namespace DndServer.Campaign.Models
{
    public class CampaignListModel
    {
        private List<GetCampaignModel> _campaignModels = new List<GetCampaignModel>();

        public List<GetCampaignModel> CampaignModels { get => _campaignModels; set => _campaignModels = value; }
    }
}

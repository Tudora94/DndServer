namespace DndServer.Campaign.Models
{
    public class GetCampaignModel
    {
        private int _id;
        private string _campaignName;

        public int Id { get => _id; set => _id = value; }
        public string CampaignName { get => _campaignName; set => _campaignName = value; }
    }
}

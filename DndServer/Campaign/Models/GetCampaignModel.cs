namespace DndServer.Campaign.Models
{
    public class GetCampaignModel
    {
        private int _campaignId;
        private int _userId;
        private string _Name;

        public int CampaignId { get => _campaignId; set => _campaignId = value; }
        public int userId { get => _userId; set => _userId = value; }
        public string Name { get => _Name; set => _Name = value; }
    }
}

namespace DndServer.Campaign.Models
{
    public class CreateCampaignModel
    {
        private int _campaignId;
        private int _userId;
        private string _name = "";

        public int CampaignId { get => _campaignId; set => _campaignId = value; }
        public int UserId { get => _userId; set => _userId = value; }
        public string Name { get => _name; set => _name = value; }
    }
}

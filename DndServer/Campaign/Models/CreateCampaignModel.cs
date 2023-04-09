namespace DndServer.Campaign.Models
{
    public class CreateCampaignModel
    {
        private int _campaignId;
        private string UserName;
        private string _name;

        public int CampaignId { get => _campaignId; set => _campaignId = value; }
        public string UserName1 { get => UserName; set => UserName = value; }
        public string Name { get => _name; set => _name = value; }
    }
}

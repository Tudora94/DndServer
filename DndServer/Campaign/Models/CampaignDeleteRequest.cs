namespace DndServer.Campaign.Models
{
    public class CampaignDeleteRequest
    {
        private int _userId;
        private int _campaignId;

        public int UserId { get => _userId; set => _userId = value; }
        public int CampaignId { get => _campaignId; set => _campaignId = value; }
    }
}

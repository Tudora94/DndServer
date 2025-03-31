namespace DndServer.Player.Models
{
    public class PlayerToCampaignResponse
    {
        private bool _success = false;
        private int? _campaignId;

        public bool Success { get => _success; set => _success = value; }
        public int? CampaignId { get => _campaignId; set => _campaignId = value; }
    }
}

namespace DndServer.Player.Models
{
    public class PlayerToCampaignResponse
    {
        private bool _success = false;
        private int? _campaignId;
        private string? _campaignName;

        public bool Success { get => _success; set => _success = value; }
        public int? CampaignId { get => _campaignId; set => _campaignId = value; }
        public string CampaignName { get => _campaignName; set => _campaignName = value; }
    }
}

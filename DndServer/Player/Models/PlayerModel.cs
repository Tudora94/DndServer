namespace DndServer.Player.Models
{
    public class PlayerModel
    {
        private int _id;
        private int? _campaignId;
        private string _name;
        private long _updateTime;

        public int Id { get => _id; set => _id = value; }
        public int? CampaignId { get => _campaignId; set => _campaignId = value; }
        public string Name { get => _name; set => _name = value; }
        public long UpdateTime { get => _updateTime; set => _updateTime = value; }
    }
}

namespace DndServer.Campaign.Models
{
    public class CreateCampaignRequestModel
    {
        private int _userId;
        private string _name = "";
        private long _updateTime;

        public int UserId { get => _userId; set => _userId = value; }
        public string Name { get => _name; set => _name = value; }
        public long UpdateTime { get => _updateTime; set => _updateTime = value; }
    }
}

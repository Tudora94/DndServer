namespace DndServer.Player.Models
{
    public class PlayerToCampaignRequest
    {
        private  int _userId;
        private  int _characterId;
        private  string _roomCode= "0";
        private long _UpdateTime;

        public int UserId { get => _userId; set => _userId = value; }
        public int CharacterId { get => _characterId; set => _characterId = value; }
        public string RoomCode { get => _roomCode; set => _roomCode = value; }
        public long UpdateTime { get => _UpdateTime; set => _UpdateTime = value; }



    }
}

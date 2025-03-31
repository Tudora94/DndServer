namespace DndServer.Player.Models
{
    public class NewCharacterModel
    {
        private string _playerName;
        private int _userId;
        private long _updateTime;

        public string Name { get => _playerName; set => _playerName = value; }
        public int UserId { get => _userId; set => _userId = value; }
        public long UpdateTime { get => _updateTime; set => _updateTime = value; }
    }
}

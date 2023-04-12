namespace DndServer.Player.Models
{
    public class NewCharacterModel
    {
        private string _roomCode;
        private string _name;
        private string _userName;

        public string RoomCode { get => _roomCode; set => _roomCode = value; }
        public string Name { get => _name; set => _name = value; }
        public string UserName { get => _userName; set => _userName = value; }
    }
}

namespace DndServer.Player.Models
{
    public class UpdateCharacterRequest : NewCharacterModel
    {
        private int _id;

        public int Id { get { return _id; } set { _id = value; } }
    }
}

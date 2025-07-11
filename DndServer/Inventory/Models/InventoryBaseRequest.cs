namespace DndServer.Inventory.Models
{
    public class InventoryBaseRequest
    {
        private int _userId;
        private long _updateTime;

        public int UserId { get { return _userId; } set { _userId = value; } }
        public long UpdateTime { get { return _updateTime; } set { _updateTime = value; } }
    }
}

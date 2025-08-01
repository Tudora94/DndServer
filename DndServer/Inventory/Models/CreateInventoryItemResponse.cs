namespace DndServer.Inventory.Models
{
    public class CreateInventoryItemResponse : InventoryBaseResponse
    {
        private int _itemId;

        public int ItemId { get { return _itemId; } set { _itemId = value; } }
    }
}

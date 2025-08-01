namespace DndServer.Inventory.Models
{
    public class UpdateInventoryItemRequest : InventoryBaseRequest
    {
        private string _itemName = "";
        private string _itemDescription = "";
        private string _itemDetail = "";
        private int _itemId;

        public string ItemName { get { return _itemName; } set { _itemName = value; } }
        public string ItemDescription { get { return _itemDescription; } set { _itemDescription = value; } }
        public string ItemDetail { get { return _itemDetail; } set { _itemDetail = value; } }
        public int ItemId { get { return _itemId; } set { _itemId = value; } }
    }
}

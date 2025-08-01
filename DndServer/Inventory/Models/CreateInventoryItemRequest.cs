namespace DndServer.Inventory.Models
{
    public class CreateInventoryItemRequest : InventoryBaseRequest
    {
        private int? _campaignId;
        private string _itemName = "";
        private string _itemDescription = "";
        private string _itemDetail = "";

        public int? CampaignId { get { return _campaignId;  } set { _campaignId = value; } }
        public string ItemName { get { return _itemName; } set { _itemName = value; } }
        public string ItemDescription { get { return _itemDescription; } set { _itemDescription = value; } }
        public string ItemDetail { get { return _itemDetail; } set { _itemDetail = value; } }
    }
}

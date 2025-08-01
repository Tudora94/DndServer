namespace DndServer.Inventory.Models
{
    public class InventoryItem
    {
        private int _id;
        private int? _campaignId;
        private int? _playerId;
        private string _itemName = "";
        private string _itemDescription = "";
        private string _itemDetail = "";
        private long _updateTime;

        public int Id { get { return _id; } set { _id = value; } }
        public int? CampaignId { get { return _campaignId; } set { _campaignId = value; } }
        public int? PlayerId { get { return _playerId; } set { _playerId = value; } }
        public string  ItemName { get { return _itemName; } set { _itemName = value;} }
        public string ItemDescription { get { return _itemDescription; } set { _itemDescription = value; } }
        public string ItemDetail { get { return _itemDetail; } set { _itemDetail = value; } }
        public long UpdateTime { get { return _updateTime; } set { _updateTime = value; } }
    }
}

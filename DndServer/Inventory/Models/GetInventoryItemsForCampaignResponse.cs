namespace DndServer.Inventory.Models
{
    public class GetInventoryItemsForCampaignResponse : InventoryBaseResponse
    {
        private List<InventoryItem>? inventoryItems;

        public List<InventoryItem>? InventoryItems { get { return inventoryItems; } set { inventoryItems = value; } }
    }
}

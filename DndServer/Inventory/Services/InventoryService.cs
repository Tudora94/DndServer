using DndServer.Dal;
using DndServer.Inventory.Models;
using System.Data;

namespace DndServer.Inventory.Services
{
    public class InventoryService
    {
        InventorySql sql = new InventorySql();

        public int CreateInventoryItemAndGetId(CreateInventoryItemRequest request)
        {
            return sql.CreateInventoryItem(request);
        }

        public bool UpdateInventoryItem(UpdateInventoryItemRequest request)
        {
            return sql.UpdateInventoryItem(request);
        }
        public List<InventoryItem>? GetInventoryItems(int userId, int campaignId)
        {
            DataTable? items = sql.getInventoryItems(userId, campaignId);

            if (items == null)
            {
                return null;
            }
            else
            {
                return dataTableToItemList(items);
            }
        }

        public void DeleteInventoryItem(int userId, int itemId, InventoryBaseResponse response)
        {
            var success = sql.delteIventoryItem(userId, itemId);

            if (success)
            {
                response.Success = true;
                response.Message = "item deleted successfully";
            }
            else
            {
                response.Message = "item failed to delete";
            }
        }

        public void assignItemToPlayer(AddItemToPlayerRequest request, InventoryBaseResponse response)
        {
            var success = sql.addItemToPlayer(request.UserId, request.ItemId, request.CampaignId, request.PlayerId, request.UpdateTime);

            if (success)
            {
                response.Success = true;
                response.Message = "item assigned to player " + request.PlayerId;
            }
            else
            {
                response.Message = "item failed to assign to player";
            }
        }

        public void getPlayerInventoryItems(int playerId, int campaignId, GetInventoryItemsForCampaignResponse response)
        {
            DataTable? items = sql.getPlayerInventory(playerId, campaignId);

            if (items == null)
            {
                response.Success = false;
                response.Message = "unable to retrieve items";
                return;
            }
            else
            {
                response.Success = true;
                response.Message = "items successfully retrieved";
                response.InventoryItems = dataTableToItemList(items);
            }
        }

        private List<InventoryItem>? dataTableToItemList(DataTable items)
        {
            List<InventoryItem>? inventoryItems = new List<InventoryItem>();
            foreach (DataRow row in items.Rows)
            {
                inventoryItems.Add(new InventoryItem()
                {
                    Id = row.Field<int>("Id"),
                    CampaignId = row.IsNull("CampaignId") ? null : row.Field<int?>("CampaignId"),
                    PlayerId = row.IsNull("PlayerId") ? null : row.Field<int?>("PlayerId"),
                    ItemName = row.Field<string>("ItemName"),
                    ItemDescription = row.Field<string>("Description"),
                    ItemDetail = row.Field<string>("Detail"),
                    UpdateTime = row.Field<long>("UpdateTime")
                });
            }
            return inventoryItems;
        }
    }
}

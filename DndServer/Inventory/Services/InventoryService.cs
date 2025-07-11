using DndServer.Dal;
using DndServer.Inventory.Models;

namespace DndServer.Inventory.Services
{
    public class InventoryService
    {
        InventorySql sql = new InventorySql();

        public int CreateInventoryItemAndGetId(CreateInventoryItemRequest request)
        {
            return sql.CreateInventoryItem(request);
        }
    }
}

using System.Net;

namespace DndServer.Inventory.Models
{
    public class InventoryBaseResponse
    {
        private string message = "";
        private bool success;

        public string Message { get { return message; } set { message = value; } }
        public bool Success { get { return success; } set { success = value; } }
    }
}

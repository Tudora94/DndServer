using DndServer.Inventory.Models;
using System.Data.SqlClient;

namespace DndServer.Dal
{
    public class InventorySql
    {
        ConnectionsSql connections = new ConnectionsSql();

        public int CreateInventoryItem(CreateInventoryItemRequest request)
        {
            try
            {
                SqlConnection conn = new SqlConnection();
                connections.SqlOpenConnection(conn);

                string setInventoryItemId = @"INSERT INTO DndDb.dbo.Inventory
    OUTPUT INSERTED.Id
    VALUES(@userId, null, null, @itemName, @itemDescription, @itemDetail, @updateTime)";

                SqlCommand cmd = new SqlCommand(setInventoryItemId, conn);

                cmd.Parameters.Add("@userId", System.Data.SqlDbType.Int).Value = request.UserId;
                cmd.Parameters.Add("@itemName", System.Data.SqlDbType.VarChar).Value = request.ItemName;
                cmd.Parameters.Add("@itemDescription", System.Data.SqlDbType.VarChar).Value = request.ItemDescription;
                cmd.Parameters.Add("@itemDetail", System.Data.SqlDbType.VarChar).Value = request.ItemDetail;
                cmd.Parameters.Add("@updateTime", System.Data.SqlDbType.BigInt).Value = request.UpdateTime;

                int ItemId = Convert.ToInt32(cmd.ExecuteScalar());

                connections.SQLCloseConnection(conn);

                return ItemId;
            }
            catch (Exception ex)
            {
                return 0;
            }

        }

        public bool UpdateInventoryItem(UpdateInventoryItemRequest request)
        {
            try
            {
                SqlConnection conn = new SqlConnection();
                connections.SqlOpenConnection(conn);

                string UpdateInventoryItem = @"UPDATE DndDb.dbo.Inventory
SET ItemName = @itemName, Description = @itemDescription, Detail = @itemDetail, UpdateTime = @updateTime
WHERE Id = @itemId AND UserId = @userId";

                SqlCommand cmd = new SqlCommand(UpdateInventoryItem, conn);

                cmd.Parameters.Add("@userId", System.Data.SqlDbType.Int).Value = request.UserId;
                cmd.Parameters.Add("@itemName", System.Data.SqlDbType.VarChar).Value = request.ItemName;
                cmd.Parameters.Add("@itemDescription", System.Data.SqlDbType.VarChar).Value = request.ItemDescription;
                cmd.Parameters.Add("@itemDetail", System.Data.SqlDbType.VarChar).Value = request.ItemDetail;
                cmd.Parameters.Add("@updateTime", System.Data.SqlDbType.BigInt).Value = request.UpdateTime;
                cmd.Parameters.Add("@itemId", System.Data.SqlDbType.Int).Value=request.ItemId;

                int rowsAffected = cmd.ExecuteNonQuery();
                connections.SQLCloseConnection(conn);
                return rowsAffected > 0;

            }
            catch(Exception ex)
            {
                return false;
            }
        }
    }
}

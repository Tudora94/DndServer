using System.Data.SqlClient;
using System.Data;
using DndServer.User.Models;
using DndServer.Campaign.Models;



namespace DndServer.Dal

{
    public class CampaignSql
    {
        private SqlConnection conn = new SqlConnection();
        ConnectionsSql connections = new ConnectionsSql();

        public int CreateCampaign(CreateCampaignModel model)
        {
            try
            {
                connections.SqlOpenConnection(conn);

                int CampaignId = 0;

                string setCampaignId = @"DndDb.dbo.CreateCampaign";
                SqlCommand cmdSetCampaignId = new SqlCommand(setCampaignId, conn);
                cmdSetCampaignId.CommandType = CommandType.StoredProcedure;

                cmdSetCampaignId.Parameters.Add("@username", SqlDbType.VarChar).Value = model.UserName1;
                cmdSetCampaignId.Parameters.Add("@CampaignName", SqlDbType.VarChar).Value = model.Name;

                cmdSetCampaignId.Parameters.Add("@CampaignId", SqlDbType.Int);
                cmdSetCampaignId.Parameters["@CampaignId"].Direction = ParameterDirection.Output;

                int i = cmdSetCampaignId.ExecuteNonQuery();

                CampaignId = Convert.ToInt32(cmdSetCampaignId.Parameters["@CampaignId"].Value);

                return CampaignId;
            }
            catch
            {
                return 0;
            }
        }

        public CampaignListModel getCampaigns(string userName)
        {
            connections.SqlOpenConnection(conn);

            string sqlString = @"Select Id, CampaignName FROM DndDb.dbo.CampaignName WHERE UserId = (SELECT Id FROM DndDb.dbo.Users WHERE username = @username)";
            SqlCommand CmdGetCampaigns = new SqlCommand(sqlString, conn);

            CmdGetCampaigns.Parameters.Add("@username", SqlDbType.VarChar).Value=userName;

            DataTable dt = new DataTable();

            SqlDataAdapter da = new SqlDataAdapter(CmdGetCampaigns);
            da.Fill(dt);
            connections.SQLCloseConnection(conn);

            CampaignListModel campaignListModel = new CampaignListModel();

            foreach(DataRow dr in dt.Rows)
            {
                GetCampaignModel getCampaignModel = new GetCampaignModel();
                getCampaignModel.Id = Convert.ToInt32(dr[0]);
                getCampaignModel.CampaignName = dr[1].ToString();
                campaignListModel.CampaignModels.Add(getCampaignModel);
            }

            return campaignListModel;
        }

    }
}

using System.Data.SqlClient;
using System.Data;
using DndServer.User.Models;
using DndServer.Campaign.Models;



namespace DndServer.Dal

{
    public class CampaignSql
    {
        ConnectionsSql connections = new ConnectionsSql();

        public int CreateCampaign(CreateCampaignModel model)
        {
            try
            {
                SqlConnection conn = new SqlConnection();
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
            SqlConnection conn = new SqlConnection();
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

        public DataSet GetCampaign(int campaignId)
        {

            SqlConnection conn = new SqlConnection();
            CampaignModel campaign = new CampaignModel();

            connections.SqlOpenConnection(conn);

            string sqlString = @"SELECT CN.Id, CN.CampaignName, CD.advancementType, CD.hpType, CD.weightType, CD.goldWeight FROM DndDb.dbo.CampaignName AS CN  JOIN Dnddb.dbo.CampaignData AS CD ON CN.Id = CD.CampaignId WHERE CN.Id = @campaignId ;" +
                @"SELECT PHB_5TH_EDITION_CONTENT, HOMEBREW_CONTENT, ONLINE_CONTENT, OTHER_SOURCE_CONTENT FROM DndDb.dbo.CampaignSourceData WHERE CampaignId = @campaignId";
            SqlCommand CmdGetCampaign = new SqlCommand(sqlString, conn);

            CmdGetCampaign.Parameters.Add("@campaignId", SqlDbType.Int).Value=campaignId;

            SqlDataAdapter adapter = new SqlDataAdapter(CmdGetCampaign);

            DataSet ds = new DataSet();
            adapter.Fill(ds);

            connections.SQLCloseConnection(conn);

            return ds;
        }

        public void setRoomCode(CampaignCode code, int campaignId)
        {
            SqlConnection conn = new SqlConnection();
            connections.SqlOpenConnection(conn);

            string sqlString = @"IF EXISTS (SELECT * FROM DndDb.dbo.CampaignRoomCode WHERE CampaignId = @CampaignId) " +
                @"BEGIN " +
                @"UPDATE DndDb.dbo.CampaignRoomCode SET CampaignCode = @CampaignCode, ExpiryDateTime = DATEADD(d,1,GETDATE()) " +
                @"WHERE CampaignId = @CampaignId END " +
                @"ELSE BEGIN INSERT INTO DndDb.dbo.CampaignRoomCode VALUES(@CampaignId, @CampaignCode, DATEADD(d,1,GETDATE())) END";

            SqlCommand cmdSetRoomCode = new SqlCommand(sqlString, conn);
            cmdSetRoomCode.Parameters.Add("@CampaignCode", SqlDbType.VarChar).Value = code.CampaignRoomCode;
            cmdSetRoomCode.Parameters.Add("@CampaignId", SqlDbType.Int).Value = campaignId;

            cmdSetRoomCode.ExecuteNonQuery();

            connections.SQLCloseConnection(conn);

        }

        public string setCampaignAttributes(CampaignModel model)
        {
            try
            {
                SqlConnection conn = new SqlConnection();
                connections.SqlOpenConnection(conn);

                string sqlString = @"DndDb.dbo.SetCampaignAttributes";
                SqlCommand cmdSetAttributes = new SqlCommand(sqlString, conn);
                cmdSetAttributes.CommandType = CommandType.StoredProcedure;


                cmdSetAttributes.Parameters.Add("@campaignId", SqlDbType.Int).Value = model.CampaignID;
                cmdSetAttributes.Parameters.Add("@campaign", SqlDbType.VarChar).Value = model.CampaignName;
                cmdSetAttributes.Parameters.Add("@phb", SqlDbType.Bit).Value = model.Sources.PHB_5TH_EDITION_CONTENT1;
                cmdSetAttributes.Parameters.Add("@home", SqlDbType.Bit).Value = model.Sources.HOMEBREW_CONTENT1;
                cmdSetAttributes.Parameters.Add("@online", SqlDbType.Bit).Value = model.Sources.ONLINE_CONTENT1;
                cmdSetAttributes.Parameters.Add("@other", SqlDbType.Bit).Value = model.Sources.OTHER_SOURCE_CONTENT1;
                cmdSetAttributes.Parameters.Add("@advancement", SqlDbType.Bit).Value = model.AdvancementType;
                cmdSetAttributes.Parameters.Add("@hpType", SqlDbType.Bit).Value = model.HpType;
                cmdSetAttributes.Parameters.Add("@weightType", SqlDbType.Bit).Value = model.WeightType;
                cmdSetAttributes.Parameters.Add("@goldWeight", SqlDbType.Bit).Value = model.GoldWeight;

                cmdSetAttributes.ExecuteNonQuery();
                connections.SQLCloseConnection(conn);

                return "Update Successful";
            }
            catch
            {
                return "Update Failed";
            }

        }

    }
}

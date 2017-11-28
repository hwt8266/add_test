/**
 * In charge of interactive with server
 */

// Query meter remain up time information
// If parameters are given by -1, server will return all data
var queryMeterRemainUpTimeInfo = function(districtID, userType, callBack){
  var param = {"districtID": districtID,"customerType": userType};
  var url = "/SMLM/api/md/remainuptime";
  
  msgPOST(url, param, callBack);
}
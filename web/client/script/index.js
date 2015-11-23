var app = angular.module('andon', []);

app.factory("incidentsAPI", function($http){
	var _getIncidents = function (){
		return $http.get('http://10.60.68.111:3000/incident/filter/open');
	};

	return{
		getIncidents: _getIncidents
	}
});

app.controller('incidentListCtrl', function(incidentsAPI, $scope, $interval){
	
	var loadIncidents = function (){
		incidentsAPI.getIncidents().success(function (data){
			$scope.incidents = data;
		}).error(function (data){
			$scope.mensagemErro = "Ocorreu um erro ao carregar a API!";
		});
	};

	loadIncidents();

	$scope.setRegressiveTime = function(incident){
		if(incident.regressiveTime < 0){
           incident.regressiveTime = (-1 * incident.regressiveTime); 
       }        
       var date  = new Date(incident.regressiveTime);  
       incident.regressiveTime = new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),  date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());
	}
    
    $scope.setPriority = function(incident){
        if(incident.dateHourLineStop == null){
            if(incident.regressiveTime > 0){
                incident.priority = "amarelo";
            }else{
                incident.priority = "vermelho";
            }
        }else{
             incident.priority = "preto";
        }
    }

	$interval(function(){
		loadIncidents();
	}, 1000);

});
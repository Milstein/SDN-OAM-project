SDN-OAM-project
===============

<h2>REST API Paths</h2>

<h3>Topology</h3>
<p>Resources availables at 'dxat.appserver.topology.resources'</p>

| URL                                         | Description                                             |
| ------------------------------------------- |:-------------------------------------------------------:|
| AppServer/webapi/topology/switches/         | Return all switches (enabled and disabled)              |
| AppServer/webapi/topology/switches/{dpid}   | Return the switch with the desired dpid                 |
| AppServer/webapi/topology/links             | Return all the links (enabled and disabled              |
| AppServer/webapi/topology/links/{src}/{dst} | Return the desired link from the source and destination |
| AppServer/webapi/topology/terminals/        | Return all the terminals (enabled and disabled)         |
| AppServer/webapi/topology/terminals/{id}    | Return the desired terminal from its identifier         |


<h3>Statistics</h3>
<p>Resources sources availables at 'dxat.appserver.stat.resources'.</p>


<h3>Manager</h3>
<p>Resources source availables at 'dxat.appserver.manager.resources'</p>

<h4> - Organizations</h4>
| URL                                         | Description                                             |
| ------------------------------------------- |:-------------------------------------------------------:|
| AppServer/webapi/manager/fullorg/all        | GET all full orgs  (this is for testing purposes)       |
| AppServer/webapi/manager/fullorg/{orgId}    | GET full org  (this is for testing purposes)            |
| AppServer/webapi/manager/org/all            | GET all transfer orgs (only orgs data, no users, flows,.)|
| AppServer/webapi/manager/org/{orgId}        | GET transer org (only org data, no users, flows,.)      |
| AppServer/webapi/manager/org                | PUSH new org (return new org with its id, null otherwise)|
| AppServer/webapi/manager/org/{orgId}        | PUT updated org (return updated org, null otherwise)    |
| AppServer/webapi/manager/org/{orgId}        | DELETE org (return org id, error message otherwise)     |

<h4> - Organization Users</h4>
| URL                                         | Description                                             |
| ------------------------------------------- |:-------------------------------------------------------:|
| AppServer/webapi/manager/fulluser/all       | GET all users  (this is for testing purposes)          |
| AppServer/webapi/manager/user/{orgId}/all   | GET all org users                                      |
| AppServer/webapi/manager/user/{orgId}/{userId}| GET org user                                          |
| AppServer/webapi/manager/user/auth          | Check authentication (QueryParams: username and pass)   |

<h4> - Organization Terminals</h4>
| URL                                         | Description                                             |
| ------------------------------------------- |:-------------------------------------------------------:|
| AppServer/webapi/manager/terminal/all       | GET all terminals                                       |   
| AppServer/webapi/manager/terminal/{orgId}/all| GET all org terminals                                  |
| AppServer/webapi/manager/terminal/{orgId}/{terminalId}| GET org terminal                              |

<h4> - Organization Flows</h4>
| URL                                         | Description                                             |
| ------------------------------------------- |:-------------------------------------------------------:|
| AppServer/webapi/manager/flow/all           | GET all flows                                           |
| AppServer/webapi/manager/flow/{orgId}/all   | GET all org flows                                       |
| AppServer/webapi/manager/flow/{orgId}/{flowId}| GET org flow                                          |


<h3>Flow tables</h3>
<p>Resources source availables at 'dxat.appserver.flows.resources'</p>

| URL                                         | Description                                             |
| ------------------------------------------- |:-------------------------------------------------------:|
| AppServer/webapi/flows/                     | Return all flows (only active in the controller)        |


<h2>LaTeX compilation tips</h2>
<p>For the publication it is recommended use the TeXstudio editor which provides you a complete free LaTeX environment for develop the publication</p>

<p>Each section  must be wrote in different files. All the figures must be stored under the figures directory in order to mantain the organization and avoid the KAOS.</p>

<p>Please, if you want insert an image, please, use a vectorial format or a lossless compression one.</p>

<p>Do not commit compilation logs and compiled files</p>




--------------------------New Templates----------------------------------------

Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (21,'${resource.referenceid}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (101,'${thresholdvalue}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (102,'${transferordernumber}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (41,'${agentname}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (42,'${agentschedule.name}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (81,'${fromwarehouse}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (82,'${towarehouse}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (83,'${resource}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (84,'${transferqty}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (141,'${status}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (2,'${name}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (61,'${inventoryno}',null,null);

Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (21,116,'Resource having name:${name} and Resource Reference:${resource.referenceid} has been created successfully','${name};${resource.referenceid}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (22,117,'Resource having Resource name:${name} and Reference:${resource.referenceid} has been updated successfully','${name};${resource.referenceid}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (23,119,'Attribute Name:${name} has been created successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (24,120,'Attribute Name:${name}  has been updated successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (101,138,'Threshold for Resource:${resource} having threshold value: ${thresholdvalue} in warehouse name: ${name} configured successfully','${resource};${thresholdvalue};${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (102,140,'Threshold for Resource:${resource} having threshold value: ${thresholdvalue} in warehouse name: ${name} deleted successfully','${resource};${thresholdvalue};${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (103,139,'Threshold for Resource:${resource} having threshold value: ${thresholdvalue} in warehouse name: ${name} Updated successfully','${resource};${thresholdvalue};${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (503,145,'ResourceType name: ${name} has been Updated successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (504,148,'ResourceSubType name:${name} has been Updated successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (121,134,'Transfer order having order number:${transferordernumber}   From warehouse name: ${fromwarehouse} to warehouse name:${towarehouse} has been created successfully','${transferordernumber};${fromwarehouse};${towarehouse}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (41,126,'Agent schedule name: ${agentschedule.name} for  Agent name:${agentname} has been created successfully','${agentschedule.name};${agentname}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (42,125,'Agent schedule name: ${agentschedule.name}  has been updated successfully','${agentschedule.name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (501,141,'ResourceType name:${name} has been created successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (502,142,'ResourceSubType name:${name} has been created successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (141,151,'InventoryNo : ${inventoryno} has been ${status} and from ${fromwarehouse} to ${towarehouse}','${inventoryno};${status};${fromwarehouse};${towarehouse}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (3,111,'Warehouse name:${name} has been created successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (4,113,'Warehouse name: ${name} has been updated successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (5,114,'Warehouse name:${name} has been deleted successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (43,130,'WarehouseType name:${name} has been created successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (44,131,'WarehouseType name:${name} has been updated successfully','${name}');
Insert into TBLMMESSAGETEMPLATE (TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values (61,135,'InventoryNo : ${inventoryno} has been created successfully','${inventoryno}');


commit;

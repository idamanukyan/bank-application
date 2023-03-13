insert into t_roles (id, name, description)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'ADMIN', 'admin');

insert into t_roles (id, name, description)
values ('d83ea94a-b821-470d-a41f-4bfcad60ad15', 'CUSTOMER', 'customer');

insert into t_permissions (id, name)
values ('a57a580a-18f7-4314-a77c-757d58bcbfa0', 'can_add_customer');
insert into t_permissions (id, name)
values ('e3f36bff-a708-45ae-9832-1d81f137a824', 'can_delete_customer');
insert into t_permissions (id, name)
values ('6ced8f4b-4200-4bc9-bf95-74f5eb56d4ad', 'can_view_customer');
insert into t_permissions (id, name)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'can_update_customer');

insert into t_permissions (id, name)
values ('06b8b07f-b49b-476e-adb9-9b814ce25e27', 'can_add_admin');
insert into t_permissions (id, name)
values ('be6743b2-0879-40f1-98f9-58fe60492010', 'can_delete_admin');
insert into t_permissions (id, name)
values ('0705d724-48ce-4376-8940-8653a3b55dd7', 'can_view_admin');
insert into t_permissions (id, name)
values ('183a2f1b-b560-45bc-81d5-cc3a1f7e78df', 'can_update_admin');

insert into t_permissions (id, name)
values ('2678e5c6-69ed-4b35-8025-3d1486f6114e', 'can_borrow_loan');
insert into t_permissions (id, name)
values ('da83b666-42c5-4b16-9dd5-6dfbc3e72a20', 'can_have_account');
insert into t_permissions (id, name)
values ('ad161327-d456-44f5-828d-7d1e4a64e7a0', 'can_make_transfers');
insert into t_permissions (id, name)
values ('3e25dd72-8933-4209-94a9-9d75d484e5d7', 'can_have_credit_card');

insert into t_roles_permissions(role_id, permission_id)
values ('d83ea94a-b821-470d-a41f-4bfcad60ad15', '2678e5c6-69ed-4b35-8025-3d1486f6114e');
insert into t_roles_permissions(role_id, permission_id)
values ('d83ea94a-b821-470d-a41f-4bfcad60ad15', 'da83b666-42c5-4b16-9dd5-6dfbc3e72a20');
insert into t_roles_permissions(role_id, permission_id)
values ('d83ea94a-b821-470d-a41f-4bfcad60ad15', 'ad161327-d456-44f5-828d-7d1e4a64e7a0');
insert into t_roles_permissions(role_id, permission_id)
values ('d83ea94a-b821-470d-a41f-4bfcad60ad15', '3e25dd72-8933-4209-94a9-9d75d484e5d7');

insert into t_roles_permissions(role_id, permission_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'a57a580a-18f7-4314-a77c-757d58bcbfa0');
insert into t_roles_permissions(role_id, permission_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'e3f36bff-a708-45ae-9832-1d81f137a824');
insert into t_roles_permissions(role_id, permission_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '6ced8f4b-4200-4bc9-bf95-74f5eb56d4ad');
insert into t_roles_permissions(role_id, permission_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '2ef58c2e-435d-469f-ae25-f6ddaea07c6d');
insert into t_roles_permissions(role_id, permission_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '06b8b07f-b49b-476e-adb9-9b814ce25e27');
insert into t_roles_permissions(role_id, permission_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', 'be6743b2-0879-40f1-98f9-58fe60492010');
insert into t_roles_permissions(role_id, permission_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '0705d724-48ce-4376-8940-8653a3b55dd7');
insert into t_roles_permissions(role_id, permission_id)
values ('2ef58c2e-435d-469f-ae25-f6ddaea07c6d', '183a2f1b-b560-45bc-81d5-cc3a1f7e78df');

--Lists a user's permissions
select u.login, p.name as profile_name, r.name as role_name 
from tb_user u
join tb_profile p on (p.id = u.profile_id)
join tb_profile_roles pr on (pr.profile_id = p.id)
join tb_role r on (r.id = pr.role_id)
where u.login = 'admin'
order by u.login, r.name
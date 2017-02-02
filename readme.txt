= Capture The Flag =



== registere lag ==
 curl -XPOST http://localhost:9090/api/team/add/new_team_name

== liste flag ==

curl -XGET -H "X-TEAM-KEY: <team-key>" http://localhost:9090/api/flag/list

== svare på flag ==

curl -H "X-TEAM-KEY: <team-key>" -XGET -H "Content-Type: application/json" -d '{ "flagId": "<flag-id>", "answer": "<ditt svar>" }' -XPOST http://localhost:9090/api/flag/
INTRUCOES DE USO


As informacoes em application.properties
client.id
client.secret
redirect.param
redirect.path
client.id.param

Devem ser alteradas de acordo com a API cadastrada e configurada dentro da plataforma Spotify



Iniciado:

-> base_url/api/auth :
Faz a solicitacao de acesso para o usuario de seus dados na spotify.

Apos o acesso dado ira ser redirecionado para outra pagina onde um numero sera exibido.
Este numero é o ID registrado para coletar as informacoes (token por exemplo) e conseguir utilizar na aplicacao.


-> base_url/api/playlists?id=:ID
Onde :ID é o ID informado apos a autenticacao com o usuario. 
Retorna a lista de playlists do usuario no spotify

-> base_url/api/add?playlistId=:PLAYLISTID&id=:ID
Onde :ID é o ID inforamdo poas a autenticaco com o usuario
:PLAYLISTID é o ID da playlist informada na busca por todos as playlists
Corpo da mensagem é um JSON da seguinte forma
{
    "uri" : [TRACKID, TRACKID, TRACKID..]
}


-> base_url/api/remove?playlistId=:PLAYLISTID&id=:ID
Remove as tracks da playlist
Onde :ID é o ID inforamdo poas a autenticaco com o usuario
:PLAYLISTID é o ID da playlist informada na busca por todos as playlists
Corpo da mensagem é um JSON da seguinte forma
{
    "tracks" : [
   {
    "uri" : TRACKID
   }
   ]
}

-> base_url/api/playlist/:PLAYLISTID
Busca informacoes de uma unica determinada playlist
:PLAYLISTID é o ID da playlist informada na busca por todos as playlists



O banco de dados está configurado para ser usado em memoria utilizando o H2. Mas pode ser utilizado o Banco que quiser,
 bastando adicionar a dependencia no POM e sua respectiva configuracao em .properties


UPDATE chain_menu set path = REPLACE(path,'/api/chain-','/api/git-')  where path like '/api/chain-%';

# Increase the number of simultaneous connection to mariadb
The default number of connections to mariadb might be limited. To increase it to 1000, put the following line in the my.cnf file for mariadb under the [mysqld] section:

<pre>
max_connections=1000
</pre>

# Increase the maximum size of packet for query

Normally the maximum size of packet for query is 1MB, to find out this value, you can run the following command in mariadb console:

<pre>
SELECT @@max_allowed_packet;
</pre>

The 1MB is too small for storing binary data such as image, to increase the limit, to say 256MB, run the following command

<pre>
SET GLOBAL max_allowed_packet=268435456;
</pre>

The above solution works best if the mariadb cannot be shutdown and restarted, but it may also be that the above solution simply does not work. 

To resolve this or if you want to make the settings permanent, put the following line
in the my.cnf file for mariadb under the [mysqld] section:

<pre>
max_allowed_packet=256M
</pre>


主要有2种，一种面向字节(8字节)，一种面向字符(Unicode)
1) 凡是Stream结尾的是面向字节，如InputStream/OutputStream
2) 凡是Reader/Writer结尾的是面向字符

InputStreamReader将InputStream转换成Reader
OutputStreamWriter将OutputStream转换成Writer


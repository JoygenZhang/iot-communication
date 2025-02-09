## v1.4.4
- 更新时间：2023.10.14
- S7: TSAP统一Remote的参数，基础值采用0x0300
- S7: 修复400PLC无法写入的问题，主要由于偷懒导致写操作最后多了一个没用的字节，1200、1500、200smart都是兼容的，老PLC兼容不了
- RTSP: 修复RtspInterleavedClient的循环线程中由于异常网络断开导致无法退出的问题
- RTSP: 修复Rtsp由于长期占用公共线程导致UDP模式最多只能开2个摄像头的问题，目前采用独立线程，不影响公共线程池

## v1.4.3
- 更新时间：2023.09.28
- S7: S200-smart修改local为0x1000
- S7: 200smart下载功能实现并测试成功，上传功能优化
- S7: MultiAddressWrite添加addString方法
- S7: 修复西门子机床的remote地址错误
- S7: 修复主动连接缺失持久化的控制
- Modbus: 添加更多适配的方法，自定义字节数据转换的格式
- Modbus: 修复主动连接缺失持久化的控制
- Net: 修复关闭的时候socket设置为null的问题
- Net: 添加底层socket通信对分包粘包的情况
- RTSP: 修复存在多个WWW-Authenticate的问题，默认使用第一个
- RTSP: 修复rtsp关闭时因通道断开导致的错误

## v1.4.2
- 更新时间：2023.08.17
- S7添加非注解形式的批量读写，可以进行多地址的任意组合，通过S7Parameter配置参数
- S7重构Datum数据类型，实现上传功能（200smart测试成功），下载功能未测试成功，可能PLC的约束，待研究
- S7修复字符串读写编码转换问题，采用GB2312
- S7修复200smart-PLC读取字符串和1200-PLC等不一样的问题
- ModbusTcp调整通信数据解析流程
- Modbus添加ModbusRtuOverTcp和ModbusAsciiOverTcp
- RTSP修复通信过程中因为缓存太小，导致报文解析错误的问题
- RtspFMp4Proxy修复缓存线程安全问题
- TcpServerBasic中的CompletableFuture替换为Thread
- 字节数组解析中添加自定义字节数组数据提取的功能，通过ByteArrayParameter配置参数进行批量读取

## v1.4.1
- 更新时间：2023.07.09
- RtspFMp4Proxy添加数据缓存，增加缓存大小，用于重新数据排序，同时将关键帧和普通帧区分开
- RtspClient修复UDP/TCP模式网络异常断开后无法退出循环的问题
- RtspInterleaved修改解析规则
- 调整S7通信中响应报文的解析和打印顺序
- S7地址格式兼容DB100.DBX0.0、DB100.DBB5、VB100、MW1

## v1.4.0
- 更新时间：2023.06.20
- 添加RTSP, RTCP, RTP, H264, MP4相关协议解析
- 添加RTSP客户端，获取视频流
- 添加RTSP+FMP4的视频流代理服务，可以构建WebSocket服务端将视频发送到WEB中进行实时监控
- S7协议添加DTL数据格式读写，PDULength采用PLC返回的值，槽号默认值设为1

## v1.3.0
- 更新时间：2023.05.17
- 添加西门子协议中NCK的寻址方式，主要针对西门子机床828D的数据访问
- S7协议添加对time、date、timeOfDay数据类型的读写
- S7协议序列化方式中添加对string、time、date、timeOfDay的解析
- 修复S7协议写string时更改第一个字节的bug（第一个字节为允许最大字节数），现在直接跳过该字节的处理
- modbus添加回调事件，用于输出交互的报文
- modbus添加短连接方式，添加readBoolean方法

## v1.2.7
- 更新时间：2023.03.16
- 修复下S7序列化读取时，由于实体类字段超过18个且读取数据量较小导致报文大小超过PDU的BUG
- S7通信支持短连接操作，默认长连接
- 添加S7协议的服务端模拟器，支持本地简单功能模拟
- 添加相关TCP和UDP服务端以及客户端的基类，便于后续使用
- 添加Slf4j日志

## v1.2.6
- 更新时间：2022.12.06
- 紧急修复RequestItem中存储区参数一直为DB块导致无法访问其他区的问题
- lombok依赖版本升级到推荐版本

## v1.2.5
- 更新时间：2022.12.01
- 修改基础的sourceTSAP和destinationTSAP
- SocketBasic的方法暴露出来
- ByteReadBuff和ByteWriteBuff添加littleEndian字段

## v1.2.4
- 更新时间：2022.11.09
- 完善pduLength的数据大小约束
- 自动分割数据量大于pduLength的报文，支持大数据量的读写

## v1.2.3
- 更新时间：2022.10.31
- 添加S7协议的序列化访问功能
- 添加字节数组序列化解析功能

## v1.2.2
- 更新时间：2022.09.29
- 添加对200smart的PLC兼容，其V区就是DB1区

## v1.2.1
- 更新时间：2022.09.27
- 重构字节数组的读写方式，添加ByteReadBuff和ByteWriteBuff两个类

## v1.2.0
- 更新时间：2022.07.08
- 添加modbusTcp通信协议

## v1.1.1
- 更新时间：2022.05.17
- 移除没用的依赖包

## v1.1.0
- 更新时间：2022.05.15
- 添加S7通信协议

## v1.0.2
- 更新时间：2022.04.28
- 修复int16,uint16,int32,uint32的解析错误

## v1.0.1
- 更新时间：2021.02.25
- 修复float32和float64数据解析成list时候的错误

## v1.0.0
- 搭建时间：2021.02.17
- 目的：为了休闲乐趣，简单小尝试
- 项目初始化，添加字节数据解析功能




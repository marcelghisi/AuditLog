CREATE TABLE `audit_event` (
  `id` bigint(20) NOT NULL,
  `event_date` datetime NOT NULL,
  `username` varchar(255) NOT NULL,
  `origin` varchar(255) NOT NULL,
  `pci` varchar(255) NULL,
  `pii` varchar(255) NULL,
  `event` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for table `auditevent`
--
ALTER TABLE `audit_event`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for table `auditevent`
--
ALTER TABLE `audit_event`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;



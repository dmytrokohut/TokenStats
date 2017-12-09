package com.dkohut.iconomi.common.interfaces;

import java.util.List;

import com.dkohut.iconomi.common.entity.TransferEvent;

public interface IFileGenerator {

	boolean generateFile(List<TransferEvent> transferEventsList);
}

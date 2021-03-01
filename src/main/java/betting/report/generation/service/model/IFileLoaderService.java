package betting.report.generation.service.model;

import java.util.List;
import java.util.Optional;

public interface IFileLoaderService
{
    public Optional<List<Bet>> getBetsFromFileLocation(String type);
}

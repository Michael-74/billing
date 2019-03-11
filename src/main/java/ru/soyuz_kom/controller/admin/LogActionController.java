package ru.soyuz_kom.controller.admin;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.LogActionUser;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.helper.CriteriaHelper;
import ru.soyuz_kom.repository.LogActionUserRepository;
import ru.soyuz_kom.repository.UserRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;

import java.util.*;

@RestController
public class LogActionController extends AdminController {

    @Autowired
    private LogActionUserRepository logActionUserRepository;

    @PostMapping({"v1/log/search"})
    @ResponseBody
    public Iterable<LogActionUser> search(@RequestBody HashMap<String, Object> data) {

        String string = "";

        for(Map.Entry<String, Object> entry : data.entrySet()) {
            if(entry.getValue() == null || entry.getValue() == "") {
                continue;
            }

            switch (entry.getKey()) {
                case "createdAt":
                    string += CriteriaHelper.parseAndBuildLessAndGreatThan(entry.getKey(), entry.getValue());
                    break;
                case "email":
                case "contract":
                case "ip":
                    string += CriteriaHelper.parseAndBuildEqualMore(entry.getKey(), entry.getValue());
                    break;
            }
        }
        Iterable<LogActionUser> logs;
        if(string.length() != 0) {
            String newString = string.substring(0, string.length() - 1);
            Node rootNode = new RSQLParser().parse(newString);
            Specification<LogActionUser> spec = rootNode.accept(new CustomRsqlVisitor<LogActionUser>());

            logs = logActionUserRepository.findAll(spec);

            return new HashSet<LogActionUser>((Collection<? extends LogActionUser>) logs);
        } else {
            return logActionUserRepository.findAll();
        }
    }
}

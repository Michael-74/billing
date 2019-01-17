package ru.soyuz_kom.rsql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

public class GenericRsqlSpecification<T> implements Specification<T> {

    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public GenericRsqlSpecification(final String property, final ComparisonOperator operator, final List<String> arguments) {
        super();
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Object> args = castArguments(root);
        final Object argument = args.get(0);
        //System.out.println("argument: " + args); // Значение
        //System.out.println("property: " + property); // Значение
        //query.orderBy(builder.desc(root.get("id")));

        switch (RsqlSearchOperation.getSimpleOperator(operator)) {
            case EQUAL: {
                if (argument.equals("true")) {
                    return builder.isTrue(root.get(property));
                } else if (argument.equals("false")) {
                    return builder.isFalse(root.get(property));
                } else if (argument instanceof String) {
                    return builder.like(root.get(property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return builder.isNull(root.get(property));
                } else {
                    return builder.equal(root.get(property), argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return builder.notLike(root.<String> get(property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return builder.isNotNull(root.get(property));
                } else {
                    return builder.notEqual(root.get(property), argument);
                }
            }
            case GREATER_THAN: {
                System.out.println("GREATER_THAN: " + argument);
                return builder.greaterThan(root.<String> get(property), argument.toString());
            }
            case GREATER_THAN_OR_EQUAL: {
                switch(property) {
                    case "createdAt":
                        return builder.greaterThanOrEqualTo(root.<Date> get(property), setDate((String) argument, 0, 0).getTime());
                    default:
                        return builder.greaterThanOrEqualTo(root.<String> get(property), argument.toString());
                }
            }
            case LESS_THAN: {
                return builder.lessThan(root.<String> get(property), argument.toString());
            }
            case LESS_THAN_OR_EQUAL: {
                switch(property) {
                    case "createdAt":
                        return builder.lessThanOrEqualTo(root.<Date>get(property), setDate((String) argument, 23, 59).getTime());
                    default:
                        return builder.lessThanOrEqualTo(root.<String>get(property), argument.toString());
                }
            }
            case IN:
                switch(property){
                    case "internet":
                        ArrayList<Integer> arr = new ArrayList<Integer>();
                        for(Object int2: args){
                            arr.add(Integer.parseInt((String) int2));
                        }
                        return root.get(property).in(arr);
                    default:
                        return root.get(property).in(args);
                }

            case NOT_IN:
                return builder.not(root.get(property).in(args));
        }

        return null;
    }

    // === private

    private List<Object> castArguments(final Root<T> root) {

        final Class<? extends Object> type = root.get(property).getJavaType();

        final List<Object> args = arguments.stream().map(arg -> {
            if (type.equals(Integer.class)) {
                return Integer.parseInt(arg);
            } else if (type.equals(Long.class)) {
                return Long.parseLong(arg);
            } else {
                return arg;
            }
        }).collect(Collectors.toList());

        return args;
    }

    /**
     * Настраиваем время
     * @param argument - строка с датой
     * @param hours - задаем конкретный час
     * @param minutes - задаем конкретные минуты
     * @return Calendar
     */
    private Calendar setDate(String argument, Integer hours, Integer minutes) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date docDate = null;
        try {
            docDate = format.parse((String) argument);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) docDate);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.HOUR, hours);
        cal.add(Calendar.DATE, 1);

        return cal;
    }

}
